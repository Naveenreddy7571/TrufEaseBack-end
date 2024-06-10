package org.truf.naveentruf.ServiceInterface;

import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.truf.naveentruf.Dtos.TrufDto;
import org.truf.naveentruf.Models.Truf;

import java.util.List;
import java.util.Optional;

public interface GroundInterface {

    ResponseEntity<Truf> addGround(TrufDto ground);

    ResponseEntity<Void> removeGround(String groundName);

    Truf UpdateGround(Truf ground);

    ResponseEntity<List<TrufDto>> getAllGrounds();

    ResponseEntity<TrufDto> getGroundById(Long Id);


}
