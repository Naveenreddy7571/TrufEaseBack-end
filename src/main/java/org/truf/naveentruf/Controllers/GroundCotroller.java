package org.truf.naveentruf.Controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.truf.naveentruf.Dtos.TrufDto;
import org.truf.naveentruf.Models.Truf;
import org.truf.naveentruf.Services.GroundService;

import java.util.List;


@RestController
@RequestMapping("/api/ground")
public class GroundCotroller {

    @Autowired
    GroundService groundservice;
    @PostMapping("/add")
    public ResponseEntity<Truf> addGround(@ModelAttribute TrufDto ground)
    {
        return groundservice.addGround(ground);

    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteGroundByName( @RequestParam("groundName") String groundName)
    {
        return groundservice.removeGround(groundName);
    }


    @GetMapping("/getallgrounds")
    public ResponseEntity<List<TrufDto>> getAllTrufs()
    {
        return groundservice.getAllGrounds();
    }

    @GetMapping("/getgroundbyid")
    public ResponseEntity<TrufDto> getgroundbyid(@RequestParam("id") Long id)
    {
        return groundservice.getGroundById(id);
    }



}
