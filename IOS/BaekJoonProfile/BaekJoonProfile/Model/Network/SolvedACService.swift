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
                guard let statusCode = response.response?.statusCode else {return}
                guard let value = response.value else {return}
                
                let networkResult = self.judgeStatus(by: statusCode, value)
                completion(networkResult)
                
            case .failure(let error):
                completion(.Error(error: error))
            }
        }
    }
    
    private func judgeStatus(by statusCode:Int, _ data: Data) -> DataState<Profile> {
        switch statusCode {
        case 200: return isValidData(data:data)
        default: return DataState.Error(error: ProfileError.DefaultError)
            
        }
    }
    
    private func isValidData(data: Data) -> DataState<Profile> {
        let decoder = JSONDecoder()
        
        guard let decodeData = try? decoder.decode(Profile.self, from: data) else {return .Error(error: ProfileError.ParsingError)}
        
        return .Success(data: decodeData)
    }
}

