/**
 * Created by Tess Daughton, September 13th 2015
 */

package Resources;

import javax.sound.sampled.*;
import java.io.*;

public class SoundLoader
{
  private Clip pcRightFoot;
  private Clip pcLeftFoot;
  private Clip backgroundMusic;
  private Clip explosion;

  /**
   * Default constructor. Loads all (three) known sound files and keep references to them.
   */
  public SoundLoader()
  {
    pcRightFoot = openWavByResourcePath("/Resources/sound_resources/fantasy_sound/Wav/Footsteps/Footstep_Dirt_02.wav");
    pcLeftFoot = openWavByResourcePath("/Resources/sound_resources/fantasy_sound/Wav/Footsteps/Footstep_Dirt_03.wav");
    backgroundMusic = openWavByResourcePath("/Resources/sound_resources/Haunted_Woods/Haunted_Woods_Loop.wav");
    //explosion = openWavByResourcePath("Resources/sound_resources/boom_pack/boom6.wav");
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
    pcRightFoot.setFramePosition(0);
    pcRightFoot.start();
  }

  /**
   * Plays sound effect for player's left foot step
   */
  public void leftFootStep()
  {
    if(pcLeftFoot.isRunning()) pcLeftFoot.stop();
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

  public void playExplosionEffect()
  { explosion.start();
  }

}
