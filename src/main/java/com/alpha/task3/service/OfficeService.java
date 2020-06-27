package com.alpha.task3.service;

import com.alpha.task3.domain.Branch;
import com.alpha.task3.domain.Queue;
import com.alpha.task3.exception.RestApiException;
import com.alpha.task3.mapper.BranchToOfficeDescriptionMapper;
import com.alpha.task3.model.OfficeDescription;
import com.alpha.task3.repository.OfficeRepository;
import com.alpha.task3.repository.QueuesRepository;
import lombok.AllArgsConstructor;
import org.apache.commons.math3.stat.regression.SimpleRegression;
import org.apache.lucene.util.SloppyMath;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.persistence.EntityNotFoundException;
import java.util.Collection;
import java.util.Map;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@AllArgsConstructor
public class OfficeService {

    private final OfficeRepository officeRepository;
    private final QueuesRepository queuesRepository;

    private final BranchToOfficeDescriptionMapper branchToOfficeDescriptionMapper;

    public OfficeDescription findById(Integer id) {
        return officeRepository.findById(id).map(branchToOfficeDescriptionMapper::map).orElseThrow(() -> new RestApiException(HttpStatus.NOT_FOUND, "branch not found"));
    }

    public OfficeDescription findNearest(Double lat, Double lon) {
        Map<Branch, Double> distancesByOffice = StreamSupport.stream(officeRepository.findAll().spliterator(), false)
                .collect(Collectors.toMap(
                        UnaryOperator.identity(),
                        office -> SloppyMath.haversinMeters(lat, lon, office.getLat(), office.getLon())));
        Map.Entry<Branch, Double> nearestOffice = distancesByOffice.entrySet().stream().min(Map.Entry.comparingByValue()).orElseThrow(EntityNotFoundException::new);
        OfficeDescription nearestOfficeDescription = branchToOfficeDescriptionMapper.map(nearestOffice.getKey());
        nearestOfficeDescription.setDistance(nearestOffice.getValue());
        return nearestOfficeDescription;
    }

    public static double distance(double lat1, double lat2, double lon1,
                                  double lon2, double el1, double el2) {

        final int R = 6371; // Radius of the earth

        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c * 1000; // convert to meters

        double height = el1 - el2;

        distance = Math.pow(distance, 2) + Math.pow(height, 2);

        return Math.sqrt(distance);
    }

    public OfficeDescription predict(Integer id, Integer dayOfWeek, Integer hourOfDay) {
        OfficeDescription officeDescription = findById(id);
        officeDescription.setDayOfWeek(dayOfWeek);
        officeDescription.setHourOfDay(hourOfDay);
        Collection<Queue> allByBranchesId = queuesRepository.findAllByBranchesId(id);
        if (CollectionUtils.isEmpty(allByBranchesId)) {
            return officeDescription;
        }
        SimpleRegression simpleRegression = new SimpleRegression();
        allByBranchesId.stream().peek(it -> {
            double waitedTime = (double) it.getEndTimeOfWait().getSeconds() - it.getStartTimeOfWait().getSeconds();
            simpleRegression.addData(calculateHourInWeek(dayOfWeek, hourOfDay), waitedTime);
        });
        officeDescription.setPredicting(Double.valueOf(simpleRegression.predict(calculateHourInWeek(dayOfWeek, hourOfDay))).intValue());
        return officeDescription;
    }

    private Double calculateHourInWeek(Integer dayOfWeek, Integer hourOfDay) {
        return Double.valueOf(dayOfWeek * 24 + hourOfDay);
    }
}
