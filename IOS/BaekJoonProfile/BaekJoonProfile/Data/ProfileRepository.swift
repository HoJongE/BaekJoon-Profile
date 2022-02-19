//
//  ProfileRepository.swift
//  BaekJoonProfile
//
//  Created by 박종호 on 2022/02/16.
//

import Foundation


class DefaultProfileRepository : ProfileRepository{

    private let profileService : SolvedACService
    
    init(solvedAcService: SolvedACService) {
        profileService = solvedAcService
    }
    func getProfile(id: String, completion : @escaping (DataState<Profile>) -> Void) {
        profileService.getProfile(id: id, completion: completion)
    }
    
    static let shared = DefaultProfileRepository(solvedAcService: SolvedACService.shared)
}



protocol ProfileRepository {
    func getProfile(id:String, completion:@escaping (DataState<Profile>) -> Void)
    
}