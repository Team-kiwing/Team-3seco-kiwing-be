package com.kw.data.domain.bundle

import com.kw.data.domain.Base
import jakarta.persistence.*

@Entity
class BundleQuestionOrder(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bundle_id", nullable = false, updatable = false)
    val bundleId: Int,

    @Column(name = "question_order_list", nullable = false)
    var questionOrderList: String = "",
) : Base()
