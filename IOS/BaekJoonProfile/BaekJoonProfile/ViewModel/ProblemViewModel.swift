//
//  ProblemViewModel.swift
//  BaekJoonProfile
//
//  Created by 박종호 on 2022/02/23.
//

import Foundation
import Combine
class ProblemViewModel : ObservableObject {
  let problemRepository : ProblemRepository
  
  @Published var problem : DataState<Problem> = DataState.Empty
  @Published var randomTierIndicator : Int = 0
  
  var cancellableSet: Set<AnyCancellable> = []
  var tier : Int  {
    switch problem {
      case .Success(data: let data): return data.level
      default: return randomTierIndicator
    }
  }
  
  init(problemRepository : ProblemRepository = DefaultProblemRepository.shared,
       initialState : DataState<Problem> = DataState.Empty) {
    self.problemRepository = problemRepository
    self.problem = initialState
    getRecommendedProblem()
  }
  
  public func getRecommendedProblem() {
    problem = DataState.Loading
    
    let timer = Timer.scheduledTimer(withTimeInterval: 0.13, repeats: true){ [weak self] _ in
      guard let self = self else {
        return
      }
      self.randomTierIndicator = Int.random(in: 0...30)
#if DEBUG
      print(self.randomTierIndicator)
#endif
    }
    
    DispatchQueue.global(qos: .userInteractive)
      .asyncAfter(deadline: DispatchTime.now() + 3){ [weak self] in
        timer.invalidate()
        guard let self = self else {
          return
        }
        self.problemRepository.getRandomProblem().sink {
          self.problem = $0
        }.store(in: &self.cancellableSet)
      }
  }
#if DEBUG
  deinit {
    print("ProblemViewModel Deinitialized")
  }
#endif
}
