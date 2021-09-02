//
//  NewsListView.swift
//  iosApp
//
//  Created by Artem on 31.08.21.
//  Copyright © 2021 orgName. All rights reserved.
//

import SwiftUI

struct NewsListView: View {
    
    var items: [ViewNewsItem]
    var onItemSelected: (ViewNewsItem) -> Void
    
    var body: some View {
        List(items) { item in
            NewsItemView(item: item)
                .onTapGesture {
                    onItemSelected(item)
                }
        }
    }
}

struct ViewNewsItem : Identifiable {
    var id: Int
    var url: String
    var title: String?
    var source: String
    var publishDate: String
    var imageUrl: String?
}

#if DEBUG
struct NewsListView_Previews: PreviewProvider {
    static var previews: some View {
        NewsListView(items:
                        Array(
                            repeating: ViewNewsItem(
                                id: 1,
                                url: "url to the news item",
                                title: "Some news item title",
                                source: "Fox News",
                                publishDate: "04.01.1997 15:23",
                                imageUrl: "https://www.kindpng.com/picc/m/565-5652618_food-artwork-pusheen-drawing-cat-free-hd-image.png"
                            ),
                            count: 5
                        ), onItemSelected: { _ in  }
        )
    }
}
#endif
