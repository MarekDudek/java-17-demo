package md;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Random;

import static com.google.common.base.Preconditions.checkArgument;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

@Slf4j
public class MainTest
{
    @Test
    void var_keyword()
    {
        var actual = 2 + 2;
        var expected = 4;
        assertThat(actual).isEqualTo(expected);
    }

    record BankAccount(String name, String number)
    {
        public BankAccount
        {
            checkArgument(name.length() > 10);
            checkArgument(number.length() > 8);
        }
    }

    @Test
    void records()
    {
        var account = new BankAccount("Marek Dudek", "123456789");
        assertThat(account.name).isEqualTo("Marek Dudek");
        assertThat(account.number).isEqualTo("123456789");
    }

    @Test
    void extended_switch()
    {
        var d = LocalDate.of(2022, 10, 29).getDayOfWeek();
        var free = switch (d)
                {
                    case MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY -> false;
                    case SATURDAY, SUNDAY -> true;
                };
        assertThat(free).isTrue();
    }

    @Test
    void extended_switch_with_code_block()
    {
        var d = LocalDate.of(2022, 10, 29).getDayOfWeek();
        var free = switch (d)
                {
                    case MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY ->
                    {
                        log.info("It is busy");
                        yield false;
                    }
                    case SATURDAY, SUNDAY ->
                    {
                        log.info("It is free");
                        yield true;
                    }
                };
        assertThat(free).isTrue();
    }

    @Test
    void instanceof_pattern_matching()
    {
        Random r = new Random();
        Object o = r.nextBoolean() ? r.nextLong() : r.nextBoolean();
        if (o instanceof Long l && l % 2 == 0)
        {
            log.info("It is even number {}", l);
        }
    }

    @Test
    void text_blocks()
    {
        var text = """
                one
                two
                three
                """;
        assertThat(text).isEqualTo("one\ntwo\nthree\n");
    }

    @Test
    void optional_or_else_throw()
    {
        @SuppressWarnings("all")
        var error = catchThrowable(() -> {
            Optional.empty().orElseThrow(() -> new IllegalStateException("readable"));
        });
        assertThat(error).hasMessage("readable");
    }
}
