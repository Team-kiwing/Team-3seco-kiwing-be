package com.kw.data.domain.bundle

import com.kw.data.domain.Base
import jakarta.persistence.*

@Entity
class BundleQuestionOrder(bundleId: Long, questionOrderList: String) : Base() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bundle_id", nullable = false, updatable = false)
    val bundleId: Long = bundleId

    @Column(name = "question_order_list", nullable = false)
    var questionOrderList: String = questionOrderList
        private set
}
