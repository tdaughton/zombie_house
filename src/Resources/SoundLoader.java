/**
 * Created by Tess Daughton, September 13th 2015
 * SoundLoader is used for loading and playing all of ZombieHouse sound effects
 * Tutorial: http://www3.ntu.edu.sg/home/ehchua/programming/java/J8c_PlayingSound.html
 * http://soundbible.com/473-Explosion-3.html
 */

package Resources;

import javax.sound.sampled.*;
import java.io.*;

public class SoundLoader
{
  private static Clip pcRightFoot;
  private static Clip pcLeftFoot;
  private static Clip backgroundMusic;
  private static Clip explosion;
  private static Clip dropTrap;
  private static Clip pickUpTrap;

  private static FloatControl gainControl;

  /**
   * Default constructor. Loads all (three) known sound files and keep references to them.
   */
  public SoundLoader()
  {
    pcRightFoot = openWavByResourcePath("/Resources/sound_resources/fantasy_sound/Wav/Footsteps/Footstep_Dirt_02.wav");
    pcLeftFoot = openWavByResourcePath("/Resources/sound_resources/fantasy_sound/Wav/Footsteps/Footstep_Dirt_03.wav");
    backgroundMusic = openWavByResourcePath("/Resources/sound_resources/Haunted_Woods/Haunted_Woods_Loop.wav");
    explosion = openWavByResourcePath("/Resources/sound_resources/boom_pack/explosion.wav");
    dropTrap = openWavByResourcePath("/Resources/sound_resources/fantasy_sound/Wav/Trap_00.wav");
    pickUpTrap = openWavByResourcePath("/Resources/sound_resources/fantasy_sound/Wav/Inventory_Open_01.wav");


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
    if(explosion.isRunning()) explosion.stop();
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
}
