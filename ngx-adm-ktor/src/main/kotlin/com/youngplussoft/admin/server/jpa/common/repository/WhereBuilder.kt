package com.youngplussoft.admin.server.jpa.common.repository

import com.querydsl.core.BooleanBuilder
import com.querydsl.core.types.Predicate
import com.querydsl.core.types.Visitor
import kotlin.reflect.KFunction1


// ref : https://stackoverflow.com/questions/23750528/handle-optional-parameters-in-querydsl
// ref : https://stackoverflow.com/questions/48709655/kotlin-pass-method-reference-to-function
class WhereBuilder : Predicate, Cloneable {
  private var delegate: BooleanBuilder

  constructor() {
    delegate = BooleanBuilder()
  }

  constructor(predicate: Predicate) {
    delegate = BooleanBuilder(predicate)
  }

  fun and(right: Predicate): WhereBuilder {
    return WhereBuilder(delegate.and(right))
  }

  fun <V> optionalAnd(param: V?, expression: () -> Predicate): WhereBuilder {
//        return this.applyIfNotNull<V>(param, this::and, expression())
    return if (param != null) {
      this.and(expression())
    } else this
  }

  private fun <V> applyIfNotNull(
    param: V?,
    funCall: KFunction1<@ParameterName(name = "right") Predicate, WhereBuilder>,
    booleanExpression: Predicate
  ): WhereBuilder {
    return if (param != null) {
      funCall(booleanExpression)
    } else this
  }

  override fun <R : Any?, C : Any?> accept(v: Visitor<R, C>?, context: C?): R? {
    return delegate.accept(v, context)
  }

  override fun getType(): Class<out Boolean> {
    return Boolean::class.java
  }

  override fun not(): Predicate {
    return delegate.not()
  }
}
