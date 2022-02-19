//
//  ProfileViewModel.swift
//  BaekJoonProfile
//
//  Created by 박종호 on 2022/02/16.
//

import Foundation
import SwiftUI

class ProfileViewModel : ObservableObject {
    @Published public private (set) var profileState : DataState<Profile> = .Empty
    
    private let profileRepository : ProfileRepository
    
    init(profileRepository : ProfileRepository = DefaultProfileRepository.shared){
        self.profileRepository = profileRepository
    }
    
    init(profileRepository : ProfileRepository = DefaultProfileRepository.shared ,dataState : DataState<Profile>){
        self.profileRepository = profileRepository
        profileState = dataState
    }
    
    public func getProfile(id:String) {
        withAnimation {
            profileState = DataState.Loading
        }
        profileRepository.getProfile(id: id){ result in
            withAnimation {
                self.profileState = result
            }
        }
    }
    
    public func logout() {
        withAnimation {
            self.profileState = DataState.Empty
        }
    }
    
}
