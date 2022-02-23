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
    @Published public private (set) var profileIdList : [String]
    
    private let profileRepository : ProfileRepository
    
    init(profileRepository : ProfileRepository = DefaultProfileRepository.shared){
        self.profileRepository = profileRepository
        self.profileIdList = profileRepository.getProfileIdList()
    }
    
    init(profileRepository : ProfileRepository = DefaultProfileRepository.shared ,dataState : DataState<Profile>){
        self.profileRepository = profileRepository
        self.profileIdList = profileRepository.getProfileIdList()
        profileState = dataState
        
    }
    
    public func getProfile(id:String) {
        profileState = DataState.Loading
        profileRepository.getProfile(id: id){ result in
            withAnimation(.spring()) {
                self.profileState = result
            }
            if case DataState.Success = result {
                self.addId(id: id)
            }
        }
    }
    
    private func addId(id: String) {
       
        guard !profileIdList.contains(id) else {
            return
        }
        var newValue = profileIdList
        newValue.append(id)
        profileRepository.saveProfileIdList(newValue)
        self.profileIdList = newValue
    }
    
    public func logout() {
        withAnimation(.spring()) {
            self.profileState = DataState.Empty
        }
    }
    
    public func deleteId(indexSet : Optional<IndexSet>){
        guard let indexSet = indexSet else {
            return
        }
        var newValue = profileIdList
        newValue.remove(atOffsets: indexSet)
        profileRepository.saveProfileIdList(newValue)
        self.profileIdList = newValue
    }
    
    public func moveId(from indexSet : IndexSet,to destination : Int){
        var newValue = profileIdList
        newValue.move(fromOffsets: indexSet, toOffset: destination)
        profileRepository.saveProfileIdList(newValue)
        self.profileIdList = newValue
    }
}
