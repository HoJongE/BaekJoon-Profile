//
//  APICall.swift
//  BaekJoonProfile
//
//  Created by JongHo Park on 2022/03/24.
//

import Foundation
import Alamofire

protocol APICall {
  var path: String {get}
  var headers: HTTPHeaders {get}
  var parameters: Parameters {get}
  var method: HTTPMethod {get}
}

extension APICall {
  func request(baseURL: String) -> DataRequest {
    return AF.request("\(baseURL)\(path)", method: method, parameters: parameters, headers: headers)
  }
}
