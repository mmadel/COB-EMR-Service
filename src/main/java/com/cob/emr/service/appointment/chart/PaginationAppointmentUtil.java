package com.cob.emr.service.appointment.chart;

import com.cob.emr.Utils.BeanFactory;
import com.cob.emr.entity.appointment.Appointment;
import com.cob.emr.model.appointment.AppointmentModel;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.ArrayList;
import java.util.List;

public class PaginationAppointmentUtil {

    public static PageImpl<AppointmentModel> getAppointmentPageContent(Page<List<Appointment>> page) {
        ModelMapper mapper = BeanFactory.getBean(ModelMapper.class);
        List<AppointmentModel> result = new ArrayList<>();

        for (int i = 0; i < page.getContent().size(); i++) {
            result.add(mapper.map(page.getContent().get(i), AppointmentModel.class));
        }
        return new PageImpl<>(result, page.getPageable(), page.getTotalElements());
    }
}
