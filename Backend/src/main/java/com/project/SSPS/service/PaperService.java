package com.project.SSPS.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.project.SSPS.dto.PaperDTO;
import com.project.SSPS.model.Paper;
import com.project.SSPS.repository.PaperRepository;
import com.project.SSPS.response.PaperResponse;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class PaperService {
    private final UserService userService;
    private final JwtService jwtService;
    private final PaperRepository paperRepository;

    public PaperService(UserService userService, JwtService jwtService, PaperRepository paperRepository) {
        this.userService = userService;
        this.jwtService = jwtService;
        this.paperRepository = paperRepository;
    }

    public PaperResponse create(PaperDTO paperDTO, HttpServletRequest request) {
        Paper paper = new Paper();
        if (paperRepository.existsByType(paperDTO.getType())) {
            throw new RuntimeException("Paper already exists");
        }

        paper.setType(paperDTO.getType());
        paper.setWidth(paperDTO.getWidth());
        paper.setHeight(paperDTO.getHeight());
        paper.setPrice(paperDTO.getPrice());
        String token = request.getHeader("Authorization").replace("Bearer ", "");
        paper.setUser(userService.getUserbyUsername(jwtService.extractUsername(token)));
        paperRepository.save(paper);
        return PaperResponse.fromPaper(paper);
    }

    public PaperResponse update(PaperDTO paperDTO, Long id) {
        Paper paper = paperRepository.findById(id).orElse(null);
        if (paper == null) {
            throw new RuntimeException("Paper not found");
        }
        paper.setType(paperDTO.getType());
        paper.setWidth(paperDTO.getWidth());
        paper.setHeight(paperDTO.getHeight());
        paper.setPrice(paperDTO.getPrice());
        paperRepository.save(paper);
        return PaperResponse.fromPaper(paper);
    }

    public PaperResponse getById(Long id) throws Exception {
        Paper paper = paperRepository.findById(id).orElse(null);
        if (paper == null) {
            throw new Exception("Paper not found");
        }
        return PaperResponse.fromPaper(paper);
    }

    public List<PaperResponse> getAll() {
        return paperRepository.findAll().stream().map(PaperResponse::fromPaper).toList();
    }

    public String delete(Long id) {
        Paper paper = paperRepository.findById(id).orElse(null);
        if (paper == null) {
            throw new RuntimeException("Paper not found");
        }
        paperRepository.deleteById(id);
        return "Paper deleted successfully";
    }
}
