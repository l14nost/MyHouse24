package lab.space.my_house_24.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lab.space.my_house_24.entity.settingsPage.Document;
import lab.space.my_house_24.repository.DocumentRepository;
import lab.space.my_house_24.service.DocumentService;
import lab.space.my_house_24.util.FileHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class DocumentServiceImpl implements DocumentService {
    private final DocumentRepository documentRepository;
    @Override
    public void delete(Long id) {
        log.info("Try to delete document by id: "+id);
        Document document = documentRepository.findById(id).orElseThrow(()->new EntityNotFoundException("Document by id "+id+" is not found"));
        if (document.getDocument()!=null){
            FileHandler.deleteFile(document.getDocument());
        }
        documentRepository.deleteById(id);
        log.info("Document was delete");
    }
}
