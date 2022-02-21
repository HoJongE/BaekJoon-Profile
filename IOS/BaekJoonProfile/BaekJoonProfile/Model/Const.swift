//
//  URLConfig.swift
//  BaekJoonProfile
//
//  Created by 박종호 on 2022/02/15.
//

import Foundation

struct Const {
    
}

extension Const {
    struct URL {
        static let BASE_URL = "https://solved.ac/api/v3/"
        static let USER_SHOW = "user/show"
        static let CLASS_IMAGE_PREFIX = "https://static.solved.ac/class/c"
        static let CLASS_IMAGE_POSTFIX = ".svg"
        static let DEFAULT_PROFILE = "https://static.solved.ac/misc/360x360/default_profile.png"
        static let WIDGET_ACTION = "profileWidget://widgetaction"
        
        static func widgetURL(id:String) -> String {
            "\(Const.URL.WIDGET_ACTION)?id=\(id)"
        }
    }
    
    struct UserDefaultsKey {
        static let ID_LIST = "profile_id_list"
    }
    
}
