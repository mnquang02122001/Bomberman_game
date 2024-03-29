package uet.oop.bomberman.entities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.powers.PowerUpBombs;
import uet.oop.bomberman.entities.powers.PowerUpFlames;
import uet.oop.bomberman.entities.powers.PowerUpSpeed;
import uet.oop.bomberman.entities.tiles.Gate;
import uet.oop.bomberman.graphics.Sprite;

import java.util.ArrayList;
import java.util.List;


public class Bomber extends Entity {

    public static boolean changeScreen = false;
    public boolean setADie;
    public int bombCount;
    public int flameCount;
    public int moveWait = 6;
    private List<Bomb> bombList = new ArrayList<>();
    private int maxSpeedCount;
    private double speedMax;
    private int countDieing;

    public Bomber(double xUnit, double yUnit, Image img) {
        super(xUnit, yUnit, img);
        life = 3;
        waitToDie = true;
        countToDie = 200;
        speedMax = 0.200;
        bombCount = 1;
        flameCount = 1;
        maxSpeedCount = 5;
        setADie = false;
        countDieing = 100;
    }

    public List<Bomb> getBombList() {
        return bombList;
    }

    public void addBomb(Bomb bomb) {
        bombList.add(bomb);
    }

    public void clearBomb() {
        bombList.clear();
    }

    public void placeBomb(double x, double y) {
        if (bombList.isEmpty())
            bombList.add(new Bomb(x, y, Sprite.bomb.getFxImage(), true));
    }

    @Override
    public void chooseImg() {
        if (setADie) img = Sprite.player_dead1.getFxImage();
        else {
            if (goNorth) {
                img = Sprite.player_up.getFxImage();
                if (moving) {
                    img = Sprite.movingSprite(Sprite.player_up_1, Sprite.player_up_2, animate, 30).getFxImage();
                }
                return;
            }

            if (goSouth) {
                img = Sprite.player_down.getFxImage();
                if (moving) {
                    img = Sprite.movingSprite(Sprite.player_down_1, Sprite.player_down_2, animate, 30).getFxImage();
                }
                return;
            }
            if (goWest) {
                img = Sprite.player_left.getFxImage();
                if (moving) {
                    img = Sprite.movingSprite(Sprite.player_left_1, Sprite.player_left_2, animate, 30).getFxImage();
                }
                return;
            }
            if (goEast) {
                img = Sprite.player_right.getFxImage();
                if (moving) {
                    img = Sprite.movingSprite(Sprite.player_right_1, Sprite.player_right_2, animate, 30).getFxImage();
                }
                return;
            } else img = Sprite.player_down.getFxImage();
        }
    }

    public void render(GraphicsContext gc) {
        if (alive) {
            chooseImg();
        } else {
            img = Sprite.player_dead1.getFxImage();
        }
        super.render(gc);
    }

    boolean checkMeetEnemy() {
        for (int i = 0; i < BombermanGame.listMonster.size(); i++) {
            double X = xUnit - BombermanGame.listMonster.get(i).xUnit;
            double Y = yUnit - BombermanGame.listMonster.get(i).yUnit;
            if (X * X + Y * Y <= 0.500 * 0.500) {
                if (BombermanGame.listMonster.get(i) instanceof Gate) {
                    changeScreen = true;
                }
                return true;
            }
        }
        return false;
    }

    void meetItem() {
        for (int i = 0; i < BombermanGame.listItem.size(); i++) {
            double X = xUnit - BombermanGame.listItem.get(i).xUnit;
            double Y = yUnit - BombermanGame.listItem.get(i).yUnit;
            if (X * X + Y * Y <= 0.500 * 0.500) {
                if (BombermanGame.listItem.get(i) instanceof PowerUpBombs) {
                    bombCount++;
                    System.out.println(BombermanGame.listItem.get(i) instanceof PowerUpBombs ? 100 : 0);
                } else if (BombermanGame.listItem.get(i) instanceof PowerUpFlames)
                    flameCount++;
                else if (BombermanGame.listItem.get(i) instanceof PowerUpSpeed) {
                    if (maxSpeedCount > 0) {
                        speedMax += 0.05;
                        maxSpeedCount--;
                    }

                }
                BombermanGame.listItem.get(i).setAlive(false);
            }

        }

    }


    public void reset() {
        xUnit = 1;
        yUnit = 1;
        updateLocationX();
        updateLocationY();
    }

    public void die() {
        if (life > 0) {

            if (waitToDie == false) {
                life--;
                waitToDie = true;
                countToDie = 200;
                setADie = true;
            }
            System.out.println(life + "/");
        } else setAlive(false);
    }

    @Override
    public void update() {
        Wait();
        if (danger) {
            if (!checkDanger()) die();
        }
        if (setADie) {
            if (countDieing > 0) {
                countDieing--;
            } else {
                reset();
                countDieing = 100;
                setADie = false;
            }
        }
        if (checkMeetEnemy()) die();

        meetItem();
        if (moveWait > 0) moveWait--;
        else {
            move(speedMax);
            moveWait = 2;
        }
        animate();
    }

}