package uet.oop.bomberman.controller;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import uet.oop.bomberman.entities.Entity;

public class Controller {
    public void input(Scene scene, Entity bomberman) {
        scene.setOnKeyPressed(keyEvent -> {
            if(keyEvent.getCode().equals(KeyCode.A) || keyEvent.getCode().equals(KeyCode.LEFT)){
                bomberman.setDirection(2);
                if (bomberman.checkSideUp()&&bomberman.checkLeft()&&bomberman.checkSideDown()) {
                    bomberman.yUnit-=0.25;
                    bomberman.setMoving(true);
                    bomberman.updateLocation();
                }
            }
            if(keyEvent.getCode().equals(KeyCode.W) || keyEvent.getCode().equals(KeyCode.UP)){
                bomberman.setDirection(0);
                if (bomberman.checkSideLeft()&&bomberman.checkUp()&&bomberman.checkSideRight()) {
                    bomberman.xUnit-=0.25;
                    bomberman.setMoving(true);
                    bomberman.updateLocation();

                }
            }
            if(keyEvent.getCode().equals(KeyCode.D) || keyEvent.getCode().equals(KeyCode.RIGHT)){
                bomberman.setDirection(3);
                if (bomberman.checkRight()&&bomberman.checkSideUp()&&bomberman.checkSideDown()) {
                    bomberman.yUnit+=0.25;
                    bomberman.setMoving(true);
                    bomberman.updateLocation();
                }
            }
            if(keyEvent.getCode().equals(KeyCode.S) || keyEvent.getCode().equals(KeyCode.DOWN)){
                bomberman.setDirection(1);
                if (bomberman.checkSideRight()&&bomberman.checkSideLeft()&&bomberman.checkDown()) {
                    bomberman.xUnit+=0.25;
                    bomberman.setMoving(true);
                    bomberman.updateLocation();
                }
            }
        });
        scene.setOnKeyReleased(keyEvent ->{
            bomberman.setMoving(false);
            bomberman.setDirection(-1);
        });
    }
}
