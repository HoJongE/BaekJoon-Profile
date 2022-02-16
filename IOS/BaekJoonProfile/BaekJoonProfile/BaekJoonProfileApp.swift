//
//  BaekJoonProfileApp.swift
//  BaekJoonProfile
//
//  Created by 박종호 on 2022/02/10.
//

import SwiftUI
import SDWebImage
import SDWebImageSVGCoder

@main
struct BaekJoonProfileApp: App {
    let profileViewModel = ProfileViewModel(profileRepository: DefaultProfileRepository.shared)
    
    init(){
        SDImageCodersManager.shared.addCoder(SDImageSVGCoder.shared)
    }
    var body: some Scene {
        
        return WindowGroup {
            ContentView()
                .environmentObject(profileViewModel)
        }
    }
}
