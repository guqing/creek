package xyz.guqing.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import xyz.guqing.app.model.entity.Log;
import xyz.guqing.app.repository.LogRepository;

/**
 * @author guqing
 * @date 2020-01-11 17:54
 */
@Service
public class LogService {
    private final LogRepository logRepository;

    @Autowired
    public LogService(LogRepository logRepository) {
        this.logRepository = logRepository;
    }

    public void saveLog(Log log) {
        logRepository.save(log);
    }

    public Page<Log> findByPage(Integer current, Integer pageSize) {
        Sort createTimeSort = Sort.by(Sort.Direction.DESC, "createTime");
        return logRepository.findAll(PageRequest.of(current - 1, pageSize, createTimeSort));
    }
}
