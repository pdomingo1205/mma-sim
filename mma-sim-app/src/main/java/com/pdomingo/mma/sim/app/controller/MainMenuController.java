package com.pdomingo.mma.sim.app.controller;

import com.pdomingo.mma.sim.api.CombatService;
import com.pdomingo.mma.sim.service.CombatServiceImpl;

public class MainMenuController {
    private CombatServiceImpl combatService = new CombatServiceImpl();
    public void start() {
        System.out.println("***** WELCOME TO MMA SIM *****");
        System.out.println("1. FIGHT");
        System.out.println("2. EXIT");

    }

}
