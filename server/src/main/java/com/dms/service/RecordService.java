package com.dms.service;

import com.dms.model.Record;
import com.dms.repository.RecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class RecordService {

    private final RecordRepository recordRepository;

    public List<Record> getAllRecords() {
        return recordRepository.findAll();
    }

    public List<Record> getAllRecordsContainingText(String text) {
        return recordRepository.findByNameContaining(text);
    }

    public void saveRecord(Record record) {
        recordRepository.save(record);
    }

    public Record validateAndGetRecord(Long id) {
        return recordRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Record not found"));
    }
}
