import { useState } from 'react';
import { createStyles, NumberInput, Slider, rem } from '@mantine/core';

const useStyles = createStyles((theme) => ({
  wrapper: {
    position: 'relative',
  },

  input: {
    height: 'auto',
    paddingTop: rem(22),
    paddingBottom: rem(3),
    borderBottomRightRadius: 0,
    borderBottomLeftRadius: 0,
  },

  label: {
    position: 'absolute',
    pointerEvents: 'none',
    paddingLeft: theme.spacing.sm,
    paddingTop: `calc(${theme.spacing.sm} / 2)`,
    zIndex: 1,
  },

  slider: {
    position: 'absolute',
    width: '100%',
    bottom: rem(-1),
  },

  thumb: {
    width: rem(16),
    height: rem(16),
  },

  track: {
    backgroundColor: theme.colorScheme === 'dark' ? theme.colors.dark[3] : theme.colors.gray[4],
  },
}));

export function SliderInput(props) {
  const { classes } = useStyles();
  const [value, setValue] = useState(2200);
  return (
    <div className={classes.wrapper}>
      <NumberInput
        value={value}
        onChange={setValue}
        label="Twoja wysokość ofiary"
        step={50}
        min={props.minimalOffering}
        max={10434}
        hideControls
        classNames={{ input: classes.input, label: classes.label }}
      />
      <Slider
        max={10434}
        min={props.minimalOffering}
        label={null}
        value={value === '' ? 0 : value}
        onChange={setValue}
        size={2}
        radius={0}
        className={classes.slider}
        classNames={{ thumb: classes.thumb, track: classes.track }}
      />
    </div>
  );
}