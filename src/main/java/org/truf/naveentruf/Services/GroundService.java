package org.truf.naveentruf.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.truf.naveentruf.Dtos.TrufDto;
import org.truf.naveentruf.Models.Truf;
import org.truf.naveentruf.Repositories.GroundRepository;
import org.truf.naveentruf.ServiceInterface.GroundInterface;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@Service
public class GroundService implements GroundInterface {

    @Autowired
    GroundRepository repository;
    @Override
    public ResponseEntity<Truf> addGround(TrufDto ground) {
        Truf truf = new Truf();
        truf.setGroundName(ground.getGroundName());
        truf.setGroundDescription(ground.getGroundDescription());
        truf.setGroundWidth(ground.getGroundWidth());
        truf.setGroundHeight(ground.getGroundHeight());
        truf.setPrice(ground.getPrice());
        MultipartFile groundImgFile = ground.getGroundImg();
        if (groundImgFile != null && !groundImgFile.isEmpty()) {
            try {
                truf.setGroundImg(groundImgFile.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        }

        Truf savedTruf = repository.save(truf);
        return ResponseEntity.ok(savedTruf);

    }



    @Override
    public ResponseEntity<Void> removeGround(String groundName) {
        try
        {
            System.out.println(groundName);
            repository.deleteByGroundName(groundName);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public Truf UpdateGround(Truf ground) {
        return null;
    }

    @Override
    public ResponseEntity<List<TrufDto>> getAllGrounds() {
        List<Truf> alltrufs = repository.findAll();
        List<TrufDto> allTrufDTOs = new ArrayList<>();
        for (Truf truf : alltrufs) {
            TrufDto trufDTO = new TrufDto();
            trufDTO.setGroundName(truf.getGroundName());
            trufDTO.setGroundDescription(truf.getGroundDescription());
            trufDTO.setGroundWidth(truf.getGroundWidth());
            trufDTO.setGroundHeight(truf.getGroundHeight());
            trufDTO.setPrice(truf.getPrice());
            allTrufDTOs.add(trufDTO);
        }
        return new ResponseEntity<>(allTrufDTOs,HttpStatus.OK);

    }

    @Override
    public ResponseEntity<TrufDto> getGroundById(Long Id) {

        Optional<Truf> trufOptional = repository.findById(Id);
        if (trufOptional.isPresent()) {
            Truf truf = trufOptional.get();
            TrufDto trufDto = new TrufDto();
            trufDto.setGroundName(truf.getGroundName());
            trufDto.setGroundDescription(truf.getGroundDescription());
            trufDto.setPrice(truf.getPrice());
            trufDto.setGroundWidth(truf.getGroundWidth());
            trufDto.setGroundHeight(truf.getGroundHeight());
            return new ResponseEntity<>(trufDto,HttpStatus.OK);
        } else {
            return  new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


}
