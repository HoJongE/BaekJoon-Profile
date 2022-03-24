//
//  MockedData.swift
//  BaekJoonProfile
//
//  Created by 박종호 on 2022/02/22.
//

import Foundation
import SwiftUI

#if DEBUG
extension PreviewDevice {
  static let previewDevices : [String] = ["iPhone 12 Pro","iPhone SE (2nd generation)","iPhone 8","iPhone 12 mini"]
}

let mockedIDList : [String] = ["koosaga","as00098","ldu2175","kiss5489","koreatlwls"]


extension Const {
  struct MockedURL {
    static let backgroundURL = "https://static.solved.ac/profile_bg/_season2020/s2020-gold4.png"
    static let badgeURL = "https://static.solved.ac/class/c5.svg"
  }
}
#endif
