package cm.ex.delivery.service;

import cm.ex.delivery.entity.Report;
import cm.ex.delivery.entity.User;
import cm.ex.delivery.repository.ReportRepository;
import cm.ex.delivery.repository.UserRepository;
import cm.ex.delivery.response.BasicResponse;
import cm.ex.delivery.service.interfaces.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    private ReportRepository reportRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public BasicResponse createReport(String report) {
        return null;
    }

    @Override
    public List<Report> listReportBySenderId(User senderId) {
        return List.of();
    }

    @Override
    public List<Report> listAllReports() {
        return List.of();
    }
}
