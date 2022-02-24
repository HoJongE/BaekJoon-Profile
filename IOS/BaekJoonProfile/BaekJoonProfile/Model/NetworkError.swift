//
//  NetworkError.swift
//  BaekJoonProfile
//
//  Created by 박종호 on 2022/02/12.
//

import Foundation

enum LoginError : String, Error {
    case EmptyID = "아이디를 입력해주세요"
    case NetworkConnection = "네트워크를 연결해주세요"
    case NotExistedID = "존재하지 않는 아이디입니다"
}

enum NetworkError : Error {
    case DefaultError
    case ParsingError
}

extension NetworkError : LocalizedError {
    public var errorDescription: String? {
        switch self {
        case .DefaultError:
            return NSLocalizedString("네트워크나 아이디를 확인해주세요", comment: "기본 에러")
        case .ParsingError:
            return NSLocalizedString("데이터 파싱 에러", comment: "데이터 파싱 에러")
        }
    }
}
