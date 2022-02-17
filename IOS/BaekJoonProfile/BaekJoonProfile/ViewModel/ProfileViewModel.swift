//
//  ProfileViewModel.swift
//  BaekJoonProfile
//
//  Created by 박종호 on 2022/02/16.
//

import Foundation

class ProfileViewModel : ObservableObject {
    @Published public private (set) var profileState : DataState<Profile> = .Empty
    @Published public var login = false
    
    
    private let profileRepository : ProfileRepository
    
    init(profileRepository : ProfileRepository = DefaultProfileRepository.shared){
        self.profileRepository = profileRepository
    }
    
    init(profileRepository : ProfileRepository = DefaultProfileRepository.shared ,dataState : DataState<Profile>){
        self.profileRepository = profileRepository
        profileState = dataState
    }
    
    public func getProfile(id:String) {
        profileRepository.getProfile(id: id){ result in
            self.profileState = result
            if case DataState.Success(data: _) = self.profileState {
                self.login = true
            }
        }
    }
    
    public func logout() {
        self.profileState = DataState.Empty
        login = false
    }
    
}
