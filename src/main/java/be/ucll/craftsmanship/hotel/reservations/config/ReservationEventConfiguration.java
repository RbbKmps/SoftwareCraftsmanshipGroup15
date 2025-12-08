package be.ucll.craftsmanship.hotel.reservations.config;

import be.ucll.craftsmanship.hotel.reservations.events.ReservationEventPublisher;
import be.ucll.craftsmanship.hotel.reservations.listeners.ReservationApprovalObserver;
import be.ucll.craftsmanship.hotel.reservations.listeners.ReservationLoggingObserver;
import be.ucll.craftsmanship.hotel.reservations.listeners.ReservationNotificationObserver;
import be.ucll.craftsmanship.hotel.reservations.listeners.RoomReservationObserver;
import be.ucll.craftsmanship.hotel.reservations.listeners.RoomUpdaterObserver;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import jakarta.annotation.PostConstruct;

@Configuration
public class ReservationEventConfiguration {
    private final ReservationEventPublisher eventPublisher;
    private final RoomReservationObserver roomReservationObserver;
    private final ReservationApprovalObserver reservationApprovalObserver;
    private final RoomUpdaterObserver roomUpdaterObserver;
    private final ReservationNotificationObserver notificationObserver;
    private final ReservationLoggingObserver loggingObserver;

    public ReservationEventConfiguration(ReservationEventPublisher eventPublisher,
                                        @Lazy RoomReservationObserver roomReservationObserver,
                                        @Lazy ReservationApprovalObserver reservationApprovalObserver,
                                        @Lazy RoomUpdaterObserver roomUpdaterObserver,
                                        @Lazy ReservationNotificationObserver notificationObserver,
                                        @Lazy ReservationLoggingObserver loggingObserver) {
        this.eventPublisher = eventPublisher;
        this.roomReservationObserver = roomReservationObserver;
        this.reservationApprovalObserver = reservationApprovalObserver;
        this.roomUpdaterObserver = roomUpdaterObserver;
        this.notificationObserver = notificationObserver;
        this.loggingObserver = loggingObserver;
    }

    @PostConstruct
    public void registerObservers() {
        eventPublisher.subscribe(roomReservationObserver);
        
        eventPublisher.subscribe(reservationApprovalObserver);
        
        eventPublisher.subscribe(roomUpdaterObserver);
        
        eventPublisher.subscribe(notificationObserver);
        
        eventPublisher.subscribe(loggingObserver);
    }
}
