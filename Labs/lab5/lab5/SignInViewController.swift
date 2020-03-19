//
//  SignInViewController.swift
//  lab5
//
//  Created by Nathanael Bennett on 3/18/20.
//  Copyright © 2020 Nathanael Bennett. All rights reserved.
//

import UIKit
import FirebaseUI

class SignInViewController: UIViewController, FUIAuthDelegate {

    //create default auth UI instance
    let authUI = FUIAuth.defaultAuthUI()
    
    
    @IBAction func signIn(_ sender: Any) {
        //start sign in
        let authViewController = authUI?.authViewController()
        //hand control off to FirebaseUI
        present(authViewController!, animated: true, completion: nil)
        
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        authUI?.delegate = self
        
        let provider : [FUIAuthProvider] = [
            FUIGoogleAuth()
        ]
        
        authUI?.providers = provider
    }
    

    func authUI(_ authUI: FUIAuth, didSignInWith user: User?, error: Error?) {
        if user != nil {
            let storyboard = UIStoryboard(name: "Main", bundle: nil)
            let vc = storyboard.instantiateViewController(identifier: "rootNav")
            
            vc.modalPresentationStyle = .fullScreen
            present(vc, animated: true, completion: nil)
        } else {
            print(error!.localizedDescription)
        }
    }
}
