//
//  SolvedACService.swift
//  BaekJoonProfile
//
//  Created by 박종호 on 2022/02/15.
//

import Foundation
import Alamofire

struct SolvedACService{
    static let shared = SolvedACService()
    
    func getProfile(id: String, completion : @escaping (DataState<Profile>) -> Void) {
        let URL = Const.URL.BASE_URL + Const.URL.USER_SHOW
        let parameters : Parameters = ["handle" : id]
        let headers : HTTPHeaders = ["Content-Type": "application/json"]
        let dataRequest = AF.request(URL,
                                     method: .get,
                                     parameters: parameters,
                                     encoding: URLEncoding.default,
                                     headers: headers
        )
        
        dataRequest.responseData { response in
            switch response.result {
                case .success:
                    guard let statusCode = response.response?.statusCode else {
                        completion(DataState.Error(error: NetworkError.DefaultError))
                        return
                    }
                    guard let value = response.value else {
                        completion(DataState.Error(error: NetworkError.DefaultError))
                        return
                    }
                    let resultParser = ResultParser()
                    let networkResult = resultParser.judgeStatus(by: statusCode, value,of: Profile.self)
                    completion(networkResult)
                    
                case .failure(let error):
                    completion(.Error(error: error))
            }
        }
    }
    
    func getRandomProblem(completion : @escaping (DataState<Problem>) -> Void) {
        let randomNumber = Int.random(in: 1000...Const.Problem.MAX_PROBLEM_COUNT)
        let URL = Const.URL.BASE_URL + Const.URL.PROBLEM
        let parameters : Parameters = ["problemId" : randomNumber]
        let headers : HTTPHeaders = ["Content-Type" : "application/json"]
        
        let dataRequest = AF.request(URL,
                                     method: .get,
                                     parameters: parameters,
                                     encoding: URLEncoding.default,
                                     headers: headers)
        
        dataRequest.responseData { response in
            switch response.result {
                case .success:
                    guard let statusCode = response.response?.statusCode else {
                        completion(DataState.Error(error: NetworkError.DefaultError))
                        return
                    }
                    guard let value = response.value else {
                        completion(DataState.Error(error: NetworkError.DefaultError))
                        return
                    }
                    let resultParser = ResultParser()
                    let networkResult = resultParser.judgeStatus(by: statusCode, value, of: Problem.self)
                    completion(networkResult)
                case .failure(let error):
                    completion(.Error(error: error))
            }
        }
    }
}

