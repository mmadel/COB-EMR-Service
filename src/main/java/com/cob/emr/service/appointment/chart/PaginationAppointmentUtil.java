package com.cob.emr.service.appointment.chart;

import com.cob.emr.Utils.BeanFactory;
import com.cob.emr.entity.appointment.Appointment;
import com.cob.emr.model.appointment.AppointmentModel;
import com.cob.emr.model.response.AppointmentResponse;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

public class PaginationAppointmentUtil {

    public static AppointmentResponse getAppointmentPageContent(Page<List<Appointment>> page) {
        ModelMapper mapper = BeanFactory.getBean(ModelMapper.class);
        List<AppointmentModel> result = new ArrayList<>();
        for (int i = 0; i < page.getContent().size(); i++) {
            result.add(mapper.map(page.getContent().get(i), AppointmentModel.class));
        }
        return AppointmentResponse.builder()
                .number_of_records(result.size())
                .number_of_matching_records((int) page.getTotalElements())
                .records(result)
                .build();
    }
}
