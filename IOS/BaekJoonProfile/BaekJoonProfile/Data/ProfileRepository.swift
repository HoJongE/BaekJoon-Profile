//
//  ProfileRepository.swift
//  BaekJoonProfile
//
//  Created by 박종호 on 2022/02/16.
//

import Foundation
import Combine
// MARK: 프로필 저장소 프로토콜
protocol ProfileRepository {
  func getProfile(id:String) -> AnyPublisher<DataState<Profile>, Never>
  func getProfileIdList() -> [String]
  func saveProfileIdList(_ list : [String])
}
// MARK: 프로필 저장소 인스턴스
class DefaultProfileRepository {
    
    private let profileService : SolvedACService
    
    init(solvedAcService: SolvedACService) {
        profileService = solvedAcService
    }
    static let shared = DefaultProfileRepository(solvedAcService: SolvedACService.shared)
}
// MARK: 프로토콜 구현부
extension DefaultProfileRepository: ProfileRepository {
  func getProfile(id: String) -> AnyPublisher<DataState<Profile>, Never> {
    profileService.getProfile(id: id)
  }
  
  func saveProfileIdList(_ list : [String]) {
    UserDefaults.standard.set(list, forKey: Const.UserDefaultsKey.ID_LIST)
  }
  
  func getProfileIdList() -> [String] {
    UserDefaults.standard.stringArray(forKey: Const.UserDefaultsKey.ID_LIST) ?? [String]()
  }
}
