package com.producerkafka.producer.service;

import com.producerkafka.producer.model.*;
import net.datafaker.Faker;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Service
public class TrainDataGenerator {
    
    private final Faker faker = new Faker();
    private final Random random = new Random();
    
    // Common UK train station CRS codes
    private static final List<String> UK_STATIONS = Arrays.asList(
            "LBG", "VIC", "PAD", "WAT", "KGX", "STP", "LST", "CHX", "EUS", "MYB",
            "BTN", "GLD", "CDF", "MAN", "LDS", "BHM", "GLW", "EDN", "LIV", "NCL"
    );
    
    private static final List<String> UK_STATION_NAMES = Arrays.asList(
            "London Bridge", "Victoria", "Paddington", "Waterloo", "Kings Cross", 
            "St Pancras", "Liverpool Street", "Charing Cross", "Euston", "Marylebone",
            "Brighton", "Guildford", "Cardiff", "Manchester", "Leeds", 
            "Birmingham", "Glasgow", "Edinburgh", "Liverpool", "Newcastle"
    );
    
    private static final List<String> OPERATORS = Arrays.asList(
            "Southern", "Thameslink", "Great Western Railway", "CrossCountry", 
            "LNER", "Virgin Trains", "ScotRail", "Northern", "Southeastern", "South Western Railway"
    );
    
    private static final List<String> OPERATOR_CODES = Arrays.asList(
            "SN", "TL", "GW", "XC", "LE", "VT", "SR", "NT", "SE", "SW"
    );
    
    /**
     * Generates a random train data object (one of the three schema types)
     */
    public Object generateRandomData() {
        int choice = random.nextInt(3);
        switch (choice) {
            case 0:
                return generateStationBoard();
            case 1:
                return generateServiceDetails();
            case 2:
            default:
                return generateDeparturesBoard();
        }
    }
    
    /**
     * Generates a StationBoard with random train services
     */
    public StationBoard generateStationBoard() {
        int numServices = random.nextInt(5) + 3; // 3-7 services
        List<ServiceItem> trainServices = new ArrayList<>();
        
        for (int i = 0; i < numServices; i++) {
            trainServices.add(generateServiceItem());
        }
        
        String stationCrs = getRandomStation();
        String stationName = getStationName(stationCrs);
        
        return StationBoard.builder()
                .trainServices(trainServices)
                .busServices(new ArrayList<>())
                .ferryServices(new ArrayList<>())
                .xmlns(XmlSerializerNamespaces.builder().count(0).build())
                .generatedAt(LocalDateTime.now())
                .locationName(stationName)
                .crs(stationCrs)
                .filterLocationName(null)
                .filtercrs(null)
                .filterType("to")
                .nrccMessages(generateNRCCMessages())
                .platformAvailable(true)
                .areServicesAvailable(true)
                .build();
    }
    
    /**
     * Generates ServiceDetails with calling points
     */
    public ServiceDetails generateServiceDetails() {
        int operatorIdx = random.nextInt(OPERATORS.size());
        String stationCrs = getRandomStation();
        
        return ServiceDetails.builder()
                .adhocAlerts(generateAdhocAlerts())
                .formation(generateFormationData())
                .previousCallingPoints(generateCallingPointsList(2, 4))
                .subsequentCallingPoints(generateCallingPointsList(3, 6))
                .generatedAt(LocalDateTime.now())
                .serviceType("train")
                .locationName(getStationName(stationCrs))
                .crs(stationCrs)
                .operator(OPERATORS.get(operatorIdx))
                .operatorCode(OPERATOR_CODES.get(operatorIdx))
                .rsid(generateRSID())
                .isCancelled(random.nextDouble() < 0.05) // 5% chance of cancellation
                .cancelReason(random.nextDouble() < 0.05 ? "Staff shortage" : null)
                .delayReason(random.nextDouble() < 0.2 ? "Signal failure" : null)
                .overdueMessage(null)
                .length(random.nextInt(8) + 4) // 4-11 coaches
                .detachFront(false)
                .isReverseFormation(false)
                .platform(String.valueOf(random.nextInt(12) + 1))
                .sta(generateTimeString())
                .eta(generateTimeString())
                .ata(null)
                .std(generateTimeString())
                .etd(generateTimeString())
                .atd(null)
                .xmlns(XmlSerializerNamespaces.builder().count(0).build())
                .build();
    }
    
    /**
     * Generates a DeparturesBoard
     */
    public DeparturesBoard generateDeparturesBoard() {
        int numDepartures = random.nextInt(4) + 2; // 2-5 departures
        List<DepartureItem> departures = new ArrayList<>();
        
        for (int i = 0; i < numDepartures; i++) {
            departures.add(DepartureItem.builder()
                    .service(generateServiceItem())
                    .crs(getRandomStation())
                    .build());
        }
        
        String stationCrs = getRandomStation();
        
        return DeparturesBoard.builder()
                .departures(departures)
                .xmlns(XmlSerializerNamespaces.builder().count(0).build())
                .generatedAt(LocalDateTime.now())
                .locationName(getStationName(stationCrs))
                .crs(stationCrs)
                .filterLocationName(null)
                .filtercrs(null)
                .filterType("to")
                .nrccMessages(generateNRCCMessages())
                .platformAvailable(true)
                .areServicesAvailable(true)
                .build();
    }
    
    /**
     * Generates a single ServiceItem
     */
    private ServiceItem generateServiceItem() {
        String originCrs = getRandomStation();
        String destCrs = getRandomStation();
        int operatorIdx = random.nextInt(OPERATORS.size());
        
        return ServiceItem.builder()
                .formation(generateFormationData())
                .origin(List.of(createServiceLocation(originCrs)))
                .destination(List.of(createServiceLocation(destCrs)))
                .currentOrigins(List.of(createServiceLocation(originCrs)))
                .currentDestinations(List.of(createServiceLocation(destCrs)))
                .rsid(generateRSID())
                .sta(generateTimeString())
                .eta(generateTimeString())
                .std(generateTimeString())
                .etd(generateTimeString())
                .platform(String.valueOf(random.nextInt(12) + 1))
                .operator(OPERATORS.get(operatorIdx))
                .operatorCode(OPERATOR_CODES.get(operatorIdx))
                .isCircularRoute(false)
                .isCancelled(random.nextDouble() < 0.05)
                .filterLocationCancelled(false)
                .serviceType("train")
                .length(random.nextInt(8) + 4)
                .detachFront(false)
                .isReverseFormation(false)
                .cancelReason(random.nextDouble() < 0.05 ? "Staff shortage" : null)
                .delayReason(random.nextDouble() < 0.2 ? "Signal failure" : null)
                .serviceID(faker.internet().uuid())
                .adhocAlerts(generateAdhocAlerts())
                .build();
    }
    
    /**
     * Creates a ServiceLocation
     */
    private ServiceLocation createServiceLocation(String crs) {
        return ServiceLocation.builder()
                .locationName(getStationName(crs))
                .crs(crs)
                .via(null)
                .futureChangeTo(null)
                .assocIsCancelled(false)
                .build();
    }
    
    /**
     * Generates FormationData with coaches
     */
    private FormationData generateFormationData() {
        int numCoaches = random.nextInt(8) + 4; // 4-11 coaches
        List<CoachData> coaches = new ArrayList<>();
        
        for (int i = 0; i < numCoaches; i++) {
            coaches.add(CoachData.builder()
                    .coachClass(i < 2 ? "First" : "Standard")
                    .toilet(ToiletAvailabilityType.builder()
                            .status("InService")
                            .value("Available")
                            .build())
                    .loading(random.nextInt(100))
                    .loadingSpecified(true)
                    .number(String.valueOf((char) ('A' + i)))
                    .build());
        }
        
        return FormationData.builder()
                .loadingCategory(LoadingCategory.builder()
                        .code("NORMAL")
                        .colour("green")
                        .image("normal.png")
                        .value("Normal service")
                        .build())
                .coaches(coaches)
                .build();
    }
    
    /**
     * Generates a list of ArrayOfCallingPoints
     */
    private List<ArrayOfCallingPoints> generateCallingPointsList(int min, int max) {
        int numPoints = random.nextInt(max - min + 1) + min;
        List<CallingPoint> callingPoints = new ArrayList<>();
        
        for (int i = 0; i < numPoints; i++) {
            String crs = getRandomStation();
            callingPoints.add(CallingPoint.builder()
                    .locationName(getStationName(crs))
                    .crs(crs)
                    .st(generateTimeString())
                    .et(generateTimeString())
                    .at(null)
                    .isCancelled(false)
                    .length(random.nextInt(8) + 4)
                    .detachFront(false)
                    .formation(null)
                    .adhocAlerts(new ArrayList<>())
                    .build());
        }
        
        return List.of(ArrayOfCallingPoints.builder()
                .callingPoint(callingPoints)
                .serviceType("train")
                .serviceChangeRequired(false)
                .assocIsCancelled(false)
                .build());
    }
    
    /**
     * Generates NRCC messages (warnings/alerts)
     */
    private List<NRCCMessage> generateNRCCMessages() {
        if (random.nextDouble() < 0.3) { // 30% chance of messages
            return List.of(NRCCMessage.builder()
                    .value("Minor delays expected due to earlier incident")
                    .build());
        }
        return new ArrayList<>();
    }
    
    /**
     * Generates adhoc alerts
     */
    private List<String> generateAdhocAlerts() {
        if (random.nextDouble() < 0.2) { // 20% chance of alerts
            return List.of("This train may be busier than usual");
        }
        return new ArrayList<>();
    }
    
    /**
     * Generates a Rail Service ID
     */
    private String generateRSID() {
        return String.format("%d%02d%02d%05d",
                LocalDateTime.now().getYear(),
                LocalDateTime.now().getMonthValue(),
                LocalDateTime.now().getDayOfMonth(),
                random.nextInt(100000));
    }
    
    /**
     * Generates a time string in HH:mm format
     */
    private String generateTimeString() {
        int hour = random.nextInt(24);
        int minute = random.nextInt(60);
        return String.format("%02d:%02d", hour, minute);
    }
    
    /**
     * Gets a random station CRS code
     */
    private String getRandomStation() {
        return UK_STATIONS.get(random.nextInt(UK_STATIONS.size()));
    }
    
    /**
     * Gets station name from CRS code
     */
    private String getStationName(String crs) {
        int index = UK_STATIONS.indexOf(crs);
        return index >= 0 ? UK_STATION_NAMES.get(index) : "Unknown Station";
    }
}

