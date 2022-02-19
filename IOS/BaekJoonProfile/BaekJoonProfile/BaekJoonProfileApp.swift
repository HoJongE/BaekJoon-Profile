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
    @StateObject var profileViewModel = ProfileViewModel(profileRepository: DefaultProfileRepository.shared)
    
    init(){
        SDImageCodersManager.shared.addCoder(SDImageSVGCoder.shared)
    }
    var body: some Scene {
        
        return WindowGroup {
            if case DataState.Success(data: let profile) = profileViewModel.profileState {
                ProfileView(profile: profile, logout: profileViewModel.logout)
            } else {
                LoginView()
                    .environmentObject(profileViewModel)
            }
        }
    }
}
