//
//  DataState.swift
//  BaekJoonProfile
//
//  Created by 박종호 on 2022/02/15.
//

import Foundation

enum DataState<T> : Equatable {
    static func == (lhs: DataState<T>, rhs: DataState<T>) -> Bool {
        switch (lhs,rhs) {
        case (Empty,Empty),
            (Loading,Loading),
            (Error(error: _),Error(error: _)),
            (Success(data: _),Success(data: _)) : return true
        default : return false
        }
    }
    
    case Empty
    case Loading
    case Error(error : Error)
    case Success(data : T)
}
