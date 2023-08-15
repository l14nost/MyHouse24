package lab.space.my_house_24.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lab.space.my_house_24.entity.settingsPage.Document;
import lab.space.my_house_24.repository.DocumentRepository;
import lab.space.my_house_24.service.DocumentService;
import lab.space.my_house_24.util.FileHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DocumentServiceImpl implements DocumentService {
    private final DocumentRepository documentRepository;
    @Override
    public void delete(Long id) {
        Document document = documentRepository.findById(id).orElseThrow(()->new EntityNotFoundException("Document by id "+id+" is not found"));
        if (document.getDocument()!=null){
            FileHandler.deleteFile(document.getDocument());
        }
        documentRepository.deleteById(id);
    }
}
