
case class Fragment(from: Int,
                    to: Int,
                    weight: Int
                   )

def toInt2(s: String): Int = (s.charAt(0) - '0') * 10 + (s.charAt(1) - '0')
