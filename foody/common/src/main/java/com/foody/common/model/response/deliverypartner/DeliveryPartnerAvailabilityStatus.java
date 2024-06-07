package com.foody.common.model.response.deliverypartner;
public enum DeliveryPartnerAvailabilityStatus {
    READY,
    NOT_AVAILABLE,
    MOVING,
    ASSIGNED,            // Assigned to an order but not yet picked up
    PICKED_UP,           // Picked up the order but not yet delivered
    DELIVERED,           // Order delivered
    ON_BREAK,            // Currently on a break
    OFFLINE,             // Not currently logged in or active
    MAINTENANCE,         // Vehicle or personal maintenance
    RETURNING_TO_BASE,   // Returning to the starting location or hub
    EMERGENCY            // In an emergency situation
}
