package com.example.musicapp.controller;

import com.example.musicapp.model.Music;
import com.example.musicapp.model.User;
import com.example.musicapp.model.UserDetails;
import com.example.musicapp.repositories.UserRepository;
import com.example.musicapp.services.MusicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Set;

@Controller
public class MusicController {
    @Autowired
    private MusicService service;

    @Autowired
    private UserRepository userService;

    @GetMapping("/")
    public String home(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return "home";
    }
    @GetMapping("/music")
    public ModelAndView getAllMusic() {
        List<Music> list = service.getAllMusic();
        return new ModelAndView("musicList", "music", list);
    }
    @GetMapping("/new_music")
    public String newMusic(){
        return "new_music";
    }

    @PostMapping("/save")
    public String addMusic(@ModelAttribute Music m){
        service.save(m);
        return "redirect:/music";
    }
    @GetMapping("/my_music")
    public String getMyMusic(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Set<Music> list=userDetails.getUser().getMyMusic();
        model.addAttribute("music", list);
        return "my_music";
    }

    @RequestMapping("/deleteMyList/{id}")
    public String deleteMyList(@PathVariable("id") int id){
        Music m=service.getMusicById(id);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User u = userDetails.getUser();
        u.removeMyMusic(m);
        return  "redirect:/my_music";
    }

    @RequestMapping("/mylist/{id}")
    public String getMyList(@PathVariable("id")int id){
        Music m=service.getMusicById(id);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User u = userDetails.getUser();
        u.addMyMusic(m);
        return "redirect:/music";
    }
    @RequestMapping("/editMusic/{id}")
    public String editMusic(@PathVariable("id") int id, Model model){
        Music m=service.getMusicById(id);
        model.addAttribute("music", m);
        return "music_edit";
    }
    @RequestMapping("/deleteMusic/{id}")
    public String deleteMusic(@PathVariable("id") int id){
        service.deleteMusicById(id);
        return "redirect:/music";
    }
}
