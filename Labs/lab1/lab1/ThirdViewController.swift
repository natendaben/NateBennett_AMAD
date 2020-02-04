//
//  ThirdViewController.swift
//  lab1
//
//  Created by Nathanael Bennett on 2/3/20.
//  Copyright © 2020 Nathanael Bennett. All rights reserved.
//

import UIKit
import AVFoundation

class ThirdViewController: UIViewController, AVAudioPlayerDelegate, AVAudioRecorderDelegate{

    //connect buttons as outlets so we can enable and disable them as necessary
    @IBOutlet weak var recordButton: UIButton!
    @IBOutlet weak var playButton: UIButton!
    @IBOutlet weak var stopButton: UIButton!
    
    //create instance of audio session, audio player, audio recorder
    let audioSession = AVAudioSession.sharedInstance()
    var audioPlayer: AVAudioPlayer?
    var audioRecorder: AVAudioRecorder?
    
    //create a constant for the name of the file where our audio will be saved
    let filename = "audio.m4a"
    
    //setup and initialization
    override func viewDidLoad() {
        super.viewDidLoad()
        
        //disable our play and our stop buttons since we won't have any audio to work with at the beginning
        playButton.isEnabled = false
        stopButton.isEnabled = false
        
        //get our path for the audio file
        let directoryPath = FileManager.default.urls(for: .documentDirectory, in: .userDomainMask)
        let docDirectory = directoryPath[0]
        let audioFileURL = docDirectory.appendingPathComponent(filename)
        print("Audio file url is \(audioFileURL)")
        
        //configure our audio session
        do {
            try audioSession.setCategory(AVAudioSession.Category.playAndRecord, mode: .default, options: .init(rawValue: 1))
        } catch {
            print("Audio session error: \(error.localizedDescription)")
        }
        
        //define our settings
        let settings = [
            AVFormatIDKey: Int(kAudioFormatMPEG4AAC), //this is our audio codec
            AVSampleRateKey: 1200, //this is our sample rate in hz
            AVNumberOfChannelsKey: 1, //we only have one channel
            AVEncoderAudioQualityKey: AVAudioQuality.high.rawValue //this is our bit rate
        ]
        
        do{
            //create recorder instance
            audioRecorder = try AVAudioRecorder(url: audioFileURL, settings: settings)
            //prepare for recording by getting file ready at path
            audioRecorder?.prepareToRecord()
            print("Audio recorder is ready!")
        } catch {
            print("Audio recorder error: \(error.localizedDescription)")
        }
    }

    //methods for playing, recording or stopping audio
    @IBAction func recordAudio(_ sender: Any) {
        //check to make sure we have an instance
        if let recorder = audioRecorder {
            if recorder.isRecording == false { //if recorder is not playing
                //enable stop button and disable play button
                recordButton.isEnabled = false
                stopButton.isEnabled = true
                playButton.isEnabled = false
                recorder.delegate = self //allows recorder to respond to errors
                recorder.record()
            }
        } else {
            print("No audio recorder instance exists!")
        }
    }
    
    @IBAction func playAudio(_ sender: Any) {
        //make sure recording is not in progress
        if audioRecorder?.isRecording == false {
            stopButton.isEnabled = true
            recordButton.isEnabled = false
            
            do {
                try audioPlayer = AVAudioPlayer(contentsOf: (audioRecorder?.url)!)
                //set everything to playback mode for optimal volume
                try audioSession.setCategory(AVAudioSession.Category.playback)
                audioPlayer!.delegate = self
                audioPlayer!.prepareToPlay() //prep file
                audioPlayer!.play() //play file
            } catch {
                print("Audio player error: \(error.localizedDescription)")
            }
        }
    }
    
    @IBAction func stopAudio(_ sender: Any) {
        //we want this to stop the audio playback or the recording, whichever is happening
        stopButton.isEnabled = false
        playButton.isEnabled = true
        recordButton.isEnabled = true
        
        if audioRecorder?.isRecording == true {
            //if recording, stop the recording
            audioRecorder?.stop()
        } else {
            //if not recording, stop playback, reset audio session mode
            audioPlayer?.stop()
            do {
                try audioSession.setCategory(AVAudioSession.Category.playAndRecord)
            } catch {
                print(error.localizedDescription)
            }
            
        }
    }
    
    //one more method for when the audioPlayer stops playing
    func audioPlayerDidFinishPlaying(_ player: AVAudioPlayer, successfully flag: Bool) {
        recordButton.isEnabled = true
        stopButton.isEnabled = false
        
        //reset the av session mode back to play and record to optimize recording
        do {
            try audioSession.setCategory(AVAudioSession.Category.playAndRecord)
        } catch {
            print(error.localizedDescription)
        }
    }
    
    /*
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        // Get the new view controller using segue.destination.
        // Pass the selected object to the new view controller.
    }
    */

}
