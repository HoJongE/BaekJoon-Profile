//
//  DataState.swift
//  BaekJoonProfile
//
//  Created by 박종호 on 2022/02/15.
//

import Foundation

enum DataState<T> {
    case Empty
    case Loading
    case Error(error : Error)
    case Success(data : T)
}
