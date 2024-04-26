package com.example.ms1.note.note;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MainController {
    private NoteRepository noteRepository;
    private NotebookRepository notebookRepository;
    private NoteService noteService;

    @RequestMapping("/")
    public String main(Model model) {

        List<Notebook> notebookList = notebookRepository.findAll();
        if(notebookList.isEmpty()) {
            Notebook notebook = new Notebook();
            notebook.setName("새노트");
            notebookRepository.save(notebook);
            return "redirect:/";
        }
        Notebook targetNotebook = notebookList.get(0);

        List<Note> noteList = noteRepository.findAll();

        if(noteList.isEmpty()) {
            noteService.saveDefault(targetNotebook);
            return "redirect:/";
        }

        model.addAttribute("noteList", noteList);
        model.addAttribute("targetNote", noteList.get(0));
        model.addAttribute("notebookList", notebookList);
        model.addAttribute("targetNotebook", targetNotebook);

        return "main";
    }
}
