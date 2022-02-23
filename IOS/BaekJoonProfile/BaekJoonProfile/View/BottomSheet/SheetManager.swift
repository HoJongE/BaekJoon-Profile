//
//  SheetManager.swift
//  BaekJoonProfile
//
//  Created by 박종호 on 2022/02/23.
//

import Foundation



class SheetManager : ObservableObject {
    @Published public var isPresent : Bool = false
    @Published public var sheetAction : SheetAction = .Description
}

extension SheetManager {
    enum SheetAction : String {
        case Description = "위젯 추가 안내"
        case IdList = "최근 조회목록"
    }
}
