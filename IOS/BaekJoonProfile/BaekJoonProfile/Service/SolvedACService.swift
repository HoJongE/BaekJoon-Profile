//
//  SolvedACService.swift
//  BaekJoonProfile
//
//  Created by 박종호 on 2022/02/15.
//

import Foundation
import Alamofire
import Combine

// MARK: - Solved AC API 정의
enum SolvedACAPI {
  case profile(id: String)
  case problem(problemNumber: Int)
}
extension SolvedACAPI: APICall {
  var path: String {
    switch self {
      case .problem:
        return Const.URL.PROBLEM
      case .profile:
        return Const.URL.USER_SHOW
    }
  }
  var headers: HTTPHeaders {
    ["Content-Type": "application/json"]
  }
  var parameters: Parameters {
    switch self {
      case .problem(let number):
        return ["problemId": number]
      case .profile(id: let id):
        return ["handle": id]
    }
  }
  var method: HTTPMethod {
    .get
  }
}
// MARK: SolvedACService
struct SolvedACService {
  static let shared = SolvedACService()
  
  func getProfile(id: String) -> AnyPublisher<DataState<Profile>, Never> {
    return SolvedACAPI.profile(id: id).request(baseURL: Const.URL.BASE_URL)
      .validate()
      .publishDecodable(type: Profile.self)
      .value()
      .map {
        DataState.Success(data: $0)
      }
      .catch {
        Just(DataState.Error(error: $0)).eraseToAnyPublisher()
      }
      .receive(on: DispatchQueue.main)
      .eraseToAnyPublisher()
  }
  
  func getRandomProblem() -> AnyPublisher<DataState<Problem>, Never> {
    let randomNumber = Int.random(in: 1000...Const.Problem.MAX_PROBLEM_COUNT)
    return SolvedACAPI.problem(problemNumber: randomNumber).request(baseURL: Const.URL.BASE_URL)
      .validate()
      .publishDecodable(type: Problem.self)
      .value()
      .map {
        DataState.Success(data: $0)
      }
      .catch {
        Just(DataState.Error(error: $0)).eraseToAnyPublisher()
      }
      .receive(on: DispatchQueue.main)
      .eraseToAnyPublisher()
  }
}

