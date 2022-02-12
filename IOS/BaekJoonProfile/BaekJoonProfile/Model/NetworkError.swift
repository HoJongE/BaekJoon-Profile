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
