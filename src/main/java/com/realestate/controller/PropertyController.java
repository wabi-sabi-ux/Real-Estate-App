package com.realestate.controller;

import com.realestate.dto.PropertyCriteria;
import com.realestate.enums.OfferType;
import com.realestate.enums.PropertyConfig;
import com.realestate.model.Broker;
import com.realestate.model.Property;
import com.realestate.repository.BrokerRepository;
import com.realestate.service.IPropertyService;
import com.realestate.service.FileStorageService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/properties")
public class PropertyController {

    private final IPropertyService propertyService;
    private final BrokerRepository brokerRepository;
    private final FileStorageService fileStorageService;

    public PropertyController(IPropertyService propertyService,
                              BrokerRepository brokerRepository,
                              FileStorageService fileStorageService) {
        this.propertyService = propertyService;
        this.brokerRepository = brokerRepository;
        this.fileStorageService = fileStorageService;
    }

    // Create a property for a broker
    @PreAuthorize("hasRole('BROKER')")
    @PostMapping
    public ResponseEntity<?> addProperty(@RequestParam Long brokerId,
                                         @RequestBody Property property) {
        Broker broker = brokerRepository.findById(brokerId)
                .orElseThrow(() -> new IllegalArgumentException("Broker not found: " + brokerId));
        property.setBroker(broker);
        Property saved = propertyService.addProperty(property);
        return ResponseEntity.ok(saved);
    }

    // Update property (optional broker change via ?brokerId=)
    @PreAuthorize("hasRole('BROKER')")
    @PutMapping
    public ResponseEntity<?> editProperty(@RequestBody Property property,
                                          @RequestParam(required = false) Long brokerId) {
        if (brokerId != null) {
            Broker broker = brokerRepository.findById(brokerId)
                    .orElseThrow(() -> new IllegalArgumentException("Broker not found: " + brokerId));
            property.setBroker(broker);
        }
        return ResponseEntity.ok(propertyService.editProperty(property));
    }

    // Delete property
    @PreAuthorize("hasRole('BROKER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> removeProperty(@PathVariable Long id) {
        propertyService.removeProperty(id);
        return ResponseEntity.ok("Property removed: " + id);
    }

    // View single
    @GetMapping("/{id}")
    public ResponseEntity<Property> viewProperty(@PathVariable Long id) {
        return ResponseEntity.ok(propertyService.viewProperty(id));
    }

    // List all
    @GetMapping
    public ResponseEntity<List<Property>> listAll() {
        return ResponseEntity.ok(propertyService.listAllProperties());
    }

    // Search: /api/properties/search?city=Pune&config=FLAT&offer=SELL&minCost=10000&maxCost=90000
    @GetMapping("/search")
    public ResponseEntity<?> search(@RequestParam(required = false) String city,
                                    @RequestParam(required = false) String config,
                                    @RequestParam(required = false) String offer,
                                    @RequestParam(required = false) Double minCost,
                                    @RequestParam(required = false) Double maxCost) {
        PropertyCriteria c = new PropertyCriteria();
        c.setCity(city);

        if (config != null && !config.isBlank()) {
            try { c.setConfig(PropertyConfig.valueOf(config.toUpperCase())); }
            catch (IllegalArgumentException ex) {
                return ResponseEntity.badRequest().body("Invalid config. Use: FLAT, SHOP, PLOT");
            }
        }
        if (offer != null && !offer.isBlank()) {
            try { c.setOffer(OfferType.valueOf(offer.toUpperCase())); }
            catch (IllegalArgumentException ex) {
                return ResponseEntity.badRequest().body("Invalid offer. Use: SELL, RENT");
            }
        }
        c.setMinCost(minCost);
        c.setMaxCost(maxCost);

        return ResponseEntity.ok(propertyService.listPropertyByCriteria(c));
    }

    // Upload 1..n images: accepts 'files' (array) or 'file' (single)
    @PreAuthorize("hasRole('BROKER')")
    @PostMapping(path = "/{id}/images", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadImages(@PathVariable Long id,
                                          @RequestParam(value = "files", required = false) MultipartFile[] files,
                                          @RequestParam(value = "file",  required = false) MultipartFile single) {
        Property prop = propertyService.viewProperty(id);

        try {
            int count = 0;

            if (files != null && files.length > 0) {
                for (MultipartFile f : files) {
                    if (f != null && !f.isEmpty()) {
                        String publicUrl = fileStorageService.storeForProperty(id, f);
                        prop.getImageUrls().add(publicUrl);
                        count++;
                    }
                }
            }
            if (single != null && !single.isEmpty()) {
                String publicUrl = fileStorageService.storeForProperty(id, single);
                prop.getImageUrls().add(publicUrl);
                count++;
            }

            if (count == 0) {
                return ResponseEntity.badRequest().body("No image files received. Use form-data with key 'files' or 'file'.");
            }

            Property saved = propertyService.editProperty(prop);
            return ResponseEntity.ok(saved);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to upload: " + e.getMessage());
        }
    }

    // Delete a specific image by its URL (must belong to this property)
    @PreAuthorize("hasRole('BROKER')")
    @DeleteMapping("/{id}/images")
    public ResponseEntity<?> deleteImage(@PathVariable Long id,
                                         @RequestParam("url") String imageUrl) {
        Property prop = propertyService.viewProperty(id);
        boolean removed = prop.getImageUrls().remove(imageUrl);
        if (!removed) {
            return ResponseEntity.badRequest().body("Image URL not found on this property.");
        }
        try {
            fileStorageService.deleteByPublicUrl(imageUrl);
            Property saved = propertyService.editProperty(prop);
            return ResponseEntity.ok(saved);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to delete: " + e.getMessage());
        }
    }

    // Helper: list just the image URLs
    @GetMapping("/{id}/images")
    public ResponseEntity<?> listImages(@PathVariable Long id) {
        Property prop = propertyService.viewProperty(id);
        return ResponseEntity.ok(prop.getImageUrls());
    }
}
