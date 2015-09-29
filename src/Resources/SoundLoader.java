/**
 * Created by Tess Daughton, September 13th 2015
 * SoundLoader is used for loading and playing all of ZombieHouse sound effects
 * Tutorial: http://www3.ntu.edu.sg/home/ehchua/programming/java/J8c_PlayingSound.html
 * http://soundbible.com/473-Explosion-3.html
 */

package Resources;

import javax.sound.sampled.*;
import java.io.*;
import java.util.Random;
import model.Zombie;
import model.Player;

public class SoundLoader
{
  private static Clip pcRightFoot;
  private static Clip pcLeftFoot;
  private static Clip backgroundMusic;
  private static Clip explosion;
  private static Clip dropTrap;
  private static Clip thud;
  private static Clip pickUpTrap;
  private static Clip zombieTalk1;
  private static Clip zombieTalk2;
  private static Clip zombieTalk3;
  private static Clip zombiePain1;
  private static Clip zombiePain2;
  private static Clip zombiePain3;
  private static Clip zombieGrunt1;
  private static Clip zombieGrunt2;
  private static Clip zombieGrunt3;
  private static Clip zombieGrunt4;
  private static Clip zombieGrunt5;
  private static Clip zombieGrunt6;
  private static Clip zombieGrunt7;
  private static Clip losingSound;
  private static Clip levelUpSound;


  private static boolean pause;

  private float pan = 0f;

  private Random rand;

  private static FloatControl gainControl;

  /**
   * Default constructor. Loads all (three) known sound files and keep references to them.
   */
  public SoundLoader(Random rand)
  {
    this.rand = rand;
    pcRightFoot = openWavByResourcePath("/Resources/sound_resources/fantasy_sound/Wav/Footsteps/Footstep_Dirt_02.wav");
    pcLeftFoot = openWavByResourcePath("/Resources/sound_resources/fantasy_sound/Wav/Footsteps/Footstep_Dirt_03.wav");
    backgroundMusic = openWavByResourcePath("/Resources/sound_resources/Haunted_Woods/Haunted_Woods_Loop.wav");
    explosion = openWavByResourcePath("/Resources/sound_resources/boom_pack/explosion.wav");
    thud = openWavByResourcePath("/Resources/sound_resources/boom_pack/thud.wav");
    dropTrap = openWavByResourcePath("/Resources/sound_resources/fantasy_sound/Wav/Trap_00.wav");
    pickUpTrap = openWavByResourcePath("/Resources/sound_resources/fantasy_sound/Wav/Inventory_Open_01.wav");
    zombieTalk1 = openWavByResourcePath("/Resources/sound_resources/zombies/dialogue.wav");
    zombieTalk2 = openWavByResourcePath("/Resources/sound_resources/zombies/dialogue2.wav");
    zombieTalk3 = openWavByResourcePath("/Resources/sound_resources/zombies/dialogue3.wav");
    zombiePain1 = openWavByResourcePath("/Resources/sound_resources/zombies/pain1.wav");
    zombiePain2 = openWavByResourcePath("/Resources/sound_resources/zombies/pain2.wav");
    zombiePain3 = openWavByResourcePath("/Resources/sound_resources/zombies/zombie-4.wav");
    zombieGrunt1 = openWavByResourcePath("/Resources/sound_resources/zombies/zombie-1.wav");
    zombieGrunt2 = openWavByResourcePath("/Resources/sound_resources/zombies/zombie-9.wav");
    zombieGrunt3 = openWavByResourcePath("/Resources/sound_resources/zombies/zombie-10.wav");
    zombieGrunt4 = openWavByResourcePath("/Resources/sound_resources/zombies/zombie-13.wav");
    zombieGrunt5 = openWavByResourcePath("/Resources/sound_resources/zombies/zombie-15.wav");
    zombieGrunt6 = openWavByResourcePath("/Resources/sound_resources/zombies/zombie-16.wav");
    zombieGrunt7 = openWavByResourcePath("/Resources/sound_resources/zombies/zombie-17.wav");
    losingSound = openWavByResourcePath("/Resources/sound_resources/fantasy_sound/Wav/Spell_02.wav");
    levelUpSound = openWavByResourcePath("/Resources/sound_resources/fantasy_sound/Wav/Spell_00.wav");


  }

  /**
   * Performs steps necessary to obtain an audio Clip from a given file path.
   * @param path  String representing path to an audio file
   * @return  Clip opened via arcane magicks and/or null pointer
   */
  private Clip openWavByResourcePath(String path)
  {
    InputStream res;
    BufferedInputStream buf;
    AudioInputStream ais;
    Clip wav;

    try
    {
      res = this.getClass().getResourceAsStream(path);
      buf = new BufferedInputStream(res);
      ais = AudioSystem.getAudioInputStream(buf);
      wav = AudioSystem.getClip();
      wav.open(ais);

      return wav;
    }
    catch (IOException | UnsupportedAudioFileException | LineUnavailableException ex)
    {
      ex.printStackTrace();
    }

    return null;
  }

  public void setPause(boolean paused)
  {
    pause = paused;
  }
  public boolean getPause()
  {
    return pause;
  }

  /**
   * Plays sound effect for player's right foot step
   */
  public void rightFootStep()
  {
    if(pcRightFoot.isRunning()) pcRightFoot.stop();
    gainControl = (FloatControl) pcRightFoot.getControl(FloatControl.Type.MASTER_GAIN);
    gainControl.setValue(-6.0f);
    pcRightFoot.setFramePosition(0);
    pcRightFoot.start();
  }

  /**
   * Plays sound effect for player's left foot step
   */
  public void leftFootStep()
  {
    if(pcLeftFoot.isRunning()) pcLeftFoot.stop();
    gainControl = (FloatControl) pcLeftFoot.getControl(FloatControl.Type.MASTER_GAIN);
    gainControl.setValue(-6.0f);
    pcLeftFoot.setFramePosition(0);
    pcLeftFoot.start();
  }

  /**
   * Starts playing background music infinitely
   */
  public void playBackgroundMusic()
  {
    backgroundMusic.loop(Clip.LOOP_CONTINUOUSLY);
  }

  /**
   * Stops playing background music without rewind. Untested: Should probably allow resuming from the same location.
   */
  public void stopBackgroundMusic()
  {
    if (backgroundMusic.isRunning()) backgroundMusic.stop();
  }

  /**
   * Plays sound effect for explosion
   */
  public void playExplosionEffect()
  {
    if(this.getPause()) return;
    gainControl = (FloatControl) explosion.getControl(FloatControl.Type.MASTER_GAIN);
    gainControl.setValue(6.0f);
    explosion.setFramePosition(0);
    explosion.start();
  }

  public void playPickUpTrap()
  {
    if(pickUpTrap.isRunning()) pickUpTrap.stop();
    pickUpTrap.setFramePosition(0);
    pickUpTrap.start();
  }

  public void playDropTrap()
  {
    if(dropTrap.isRunning()) dropTrap.stop();
    dropTrap.setFramePosition(0);
    dropTrap.start();
  }


  public void playRandomDialogue(Zombie zombie, Player player)
  {
    if(this.getPause()) return;

    Clip zombieTalk = zombieTalk1;
    int r = rand.nextInt(3);
    if (zombie.getX() - 20 < player.getX()) pan = -1f;
    else pan = 1f;

    if (r % 3 == 0) zombieTalk = zombieTalk1;
    else if (r % 3 == 1) zombieTalk = zombieTalk2;
    else zombieTalk = zombieTalk3;

    gainControl = (FloatControl) zombieTalk.getControl(FloatControl.Type.MASTER_GAIN);
    gainControl.setValue(pan);
    if (zombieTalk.isRunning()) return;

    else
    {
      zombieTalk.setFramePosition(0);
      zombieTalk.start();
    }
  }
  public void playRandomGrunt(Zombie zombie, Player player)
  {
    if(this.getPause()) return;

    Clip zombieGrunt = zombieGrunt1;
    int r = rand.nextInt(7);
    if(zombie.getX()-20 < player.getX()) pan = -1f;
    else pan = 1f;

    if(r%7==0);
    else if(r%7==1) zombieGrunt = zombieGrunt2;
    else if(r%7==2) zombieGrunt = zombieGrunt3;
    else if(r%7==3) zombieGrunt = zombieGrunt4;
    else if(r%7==4) zombieGrunt = zombieGrunt5;
    else if(r%7==5) zombieGrunt = zombieGrunt6;
    else zombieGrunt = zombieGrunt7;


    gainControl = (FloatControl) zombieGrunt.getControl(FloatControl.Type.MASTER_GAIN);
    gainControl.setValue(pan);
    if(zombieGrunt.isRunning()) return;
    else
    {
      zombieGrunt.setFramePosition(0);
      zombieGrunt.start();
    }
  }
  public void playRandomPain(Zombie zombie, Player player)
  {
    if(this.getPause()) return;

    Clip zombiePain = zombiePain1;
    int r = rand.nextInt(3);
    if(zombie.getX()-20 < player.getX()) pan = -1f;
    else pan = 1f;

    if(r%3==1) zombiePain = zombiePain1;
    else if(r%3==2) zombiePain = zombiePain2;
    else if(r%3==0) zombiePain = zombiePain3;

    gainControl = (FloatControl) zombiePain.getControl(FloatControl.Type.MASTER_GAIN);
    gainControl.setValue(pan);
    if(zombiePain.isRunning()) return;
    else
    { zombiePain.setFramePosition(0);
      zombiePain.start();
    }
  }

  public void playLosingSound()
  {
    if(losingSound.isRunning()) losingSound.stop();
    losingSound.setFramePosition(0);
    losingSound.start();
  }

  public void playLevelUpSound()
  {
    if(levelUpSound.isRunning()) levelUpSound.stop();
    levelUpSound.setFramePosition(0);
    levelUpSound.start();
  }

  public void playThud()
  {
    if(thud.isRunning()) thud.stop();
    thud.setFramePosition(0);
    thud.start();
  }
}
