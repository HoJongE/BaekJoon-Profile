//
//  ProblemRepository.swift
//  BaekJoonProfile
//
//  Created by 박종호 on 2022/02/23.
//

import Foundation
import Combine
// MARK: Problem Repository 인스턴스
class DefaultProblemRepository  {
  private let solvedACService : SolvedACService
  
  init(solvedACService : SolvedACService = SolvedACService.shared){
    self.solvedACService = solvedACService
  }
  
  public static let shared = DefaultProblemRepository(solvedACService: SolvedACService.shared)
  
  
}
// MARK: Problem Repository 구현부
extension DefaultProblemRepository: ProblemRepository {
  func getRandomProblem() -> AnyPublisher<DataState<Problem>, Never> {
    solvedACService.getRandomProblem()
  }
}
// MARK: problem Repository 프로토콜
protocol ProblemRepository {
  func getRandomProblem() -> AnyPublisher<DataState<Problem>, Never>
}
