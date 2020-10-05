package com.example.covidtracker.controllers;

import com.example.covidtracker.models.LocationStats;
import com.example.covidtracker.services.CoronaVirusDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {

    final CoronaVirusDataService coronaVirusDataService;

    public HomeController(CoronaVirusDataService coronaVirusDataService) {
        this.coronaVirusDataService = coronaVirusDataService;
    }

    @GetMapping("/")
    public String home(Model model){
        List<LocationStats> stats = coronaVirusDataService.getStats();
        int totalReportedCases = stats.stream().mapToInt(LocationStats::getLatestTotalCases).sum();
        int totalNewCases = stats.stream().mapToInt(LocationStats::getDiffFromPrevDay).sum();
        model.addAttribute("totalReportedCases", totalReportedCases);
        model.addAttribute("locationStats", coronaVirusDataService.getStats());
        model.addAttribute("totalNewCases", totalNewCases);

        return "home";
    }
}
