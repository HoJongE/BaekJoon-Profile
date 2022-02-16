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
    
    public private(set) var profile : Profile? = nil
    
    private let profileRepository : ProfileRepository
    
    init(profileRepository : ProfileRepository){
        self.profileRepository = profileRepository
    }
    
    public func getProfile(id:String) {
        profileRepository.getProfile(id: id){ result in
            self.profileState = result
            if case DataState.Success(data: let data) = self.profileState {
                self.profile = data
                self.login = true
            }
        }
    }
    
    public func logout() {
        self.profileState = DataState.Empty
        login = false
    }
    
}
